package server;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.*;
import java.util.concurrent.Future;

@Controller
public class RootController {

	@RequestMapping(method = RequestMethod.GET, value = "/")
	public String provideUploadInfo(Model model) {
		return "uploadForm";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/")
	public ResponseEntity handleFileUpload(@RequestParam("content") MultipartFile contentFile,
								   @RequestParam("style") MultipartFile styleFile,
								   RedirectAttributes redirectAttributes) throws FileNotFoundException {

		// Make sure both files are not empty.
		if (!contentFile.isEmpty() || !styleFile.isEmpty()) {
			try {

				// Write the content image.
				String extension = "";
				int i = contentFile.getOriginalFilename().lastIndexOf('.');
				if (i > 0) {
					extension = contentFile.getOriginalFilename().substring(i+1);
				}
				File content = File.createTempFile("content","." + extension);
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(content));
                FileCopyUtils.copy(contentFile.getInputStream(), stream);
				stream.close();

				// Write the style image.
				extension = "";
				i = styleFile.getOriginalFilename().lastIndexOf('.');
				if (i > 0) {
					extension = styleFile.getOriginalFilename().substring(i+1);
				}
				File style = File.createTempFile("style", "." + extension);
				stream = new BufferedOutputStream(
						new FileOutputStream(style));
				FileCopyUtils.copy(styleFile.getInputStream(), stream);
				stream.close();

				redirectAttributes.addFlashAttribute("message",
						"You successfully uploaded the files!");

				// Submit the image processing task to the thread.
				Future<File> futureOutput = Application.executor.submit(() -> {
					try {
						return ImageProcessor.process(content,style);
					} catch (IOException e) {
						e.printStackTrace();
					}
					return null;
				});

				File output = futureOutput.get();

				content.delete();
				style.delete();
				// After having successfully preparing the content and style, we push a new task to have them processed.
				return downloadImage(output);
			}
			catch (Exception e) {
				redirectAttributes.addFlashAttribute("message",
						"You failed to upload => " + e.getMessage());
			}
		}
		else {
			redirectAttributes.addFlashAttribute("message",
					"You failed to upload because a file was empty");
			return ResponseEntity.badRequest().body("empty file(s)");
		}

		return ResponseEntity.badRequest().body("upload failed");
	}

	private ResponseEntity<InputStreamResource> downloadImage(File output) throws FileNotFoundException {
		FileInputStream fileInputStream = new FileInputStream(output);

		return ResponseEntity.ok()
				.contentLength(output.length())
				.contentType(MediaType.parseMediaType("image/png"))
				.body(new InputStreamResource(fileInputStream));
	}

}
