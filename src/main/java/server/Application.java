package server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class Application {

    /**
     * These are very important variables you need to set:
     * ROOT = the directory in which you save the uploaded files and the output file.
     * NEURAL_HOME = the complete path to your neural style installation.
     * NVIDIA_GPU_WITH_CUDA = if you've installed all the necessary prerequisites for neural style's CUDA and CUDnn, set this to true.
     */
    public static String NEURAL_HOME = "/root/neural-style";
    public static boolean NVIDIA_GPU_WITH_CUDA = true;

    public static ExecutorService executor = Executors.newSingleThreadExecutor();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}