package kenny.nio_file_copy;

import java.io.File;

interface FileCopyRunner {
    void copyFile(File source, File target);
}
public class FileCopyDemo {

    public static void main(String[] args) {
        FileCopyRunner noBufferStreamCopy;
        FileCopyRunner bufferStreamCopy;
        FileCopyRunner nioBufferCopy;
        FileCopyRunner noTransferCopy;
    }
}
