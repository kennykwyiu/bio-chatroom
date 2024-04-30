package kenny.nio_file_copy;

import java.io.*;

interface FileCopyRunner {
    void copyFile(File source, File target);
}
public class FileCopyDemo {

    private static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        FileCopyRunner noBufferStreamCopy = new FileCopyRunner() {
            @Override
            public void copyFile(File source, File target) {
                InputStream fileIn = null;
                OutputStream fileOut = null;

                try {
                    fileIn = new FileInputStream(source);
                    fileOut = new FileOutputStream(target);

                    int result;
                     while ((result = fileIn.read()) != -1) {
                         fileOut.write(result);
                     }
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } finally {
                    close(fileIn);
                    close(fileOut);
                }
            }
        };

        FileCopyRunner bufferStreamCopy;

        FileCopyRunner nioBufferCopy;

        FileCopyRunner noTransferCopy;
    }
}
