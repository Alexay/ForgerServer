package server;

import java.io.*;

/**
 * This class is used by the Controller to initiate the image processing task and returns the resulting image
 * to be sent back to the client.
 */

public class ImageProcessor {

    public static File process(File content, File style) throws IOException {

        // We initialize the process and we check whether the computer has an Nvidia GPU with CUDA support.
        // Depending on the computer's GPU we may or may not be able to use the more efficient processing method.
        Process process;
        if (Application.NVIDIA_GPU_WITH_CUDA) {
            process = new ProcessBuilder("th", "neural_style.lua", "-style_image", style.getAbsolutePath(), "-content_image", content.getAbsolutePath(),
                    "-init", "image", "-content_weight", "10", "-style_weight", "1000", "-save_iter", "0", "-gpu", "0", "-backend", "cudnn", "-cudnn_autotune")
                    .directory(new File(Application.NEURAL_HOME))
                    .start();
        } else {
            process = new ProcessBuilder("th", "neural_style.lua", "-style_image", style.getAbsolutePath(), "-content_image", content.getAbsolutePath(),
                    "-init", "image", "-content_weight", "10", "-style_weight", "1000", "-save_iter", "0", "-gpu", "1")
                    .directory(new File(Application.NEURAL_HOME))
                    .start();
        }
        InputStream is = process.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line;


        System.out.println("Output of running is:");

        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }

        return new File(Application.NEURAL_HOME + "/out.png");
    }
}
