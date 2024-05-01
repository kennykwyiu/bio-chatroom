package kenny.nio_file_copy;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

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

        FileCopyRunner bufferStreamCopy = new FileCopyRunner() {
            @Override
            public void copyFile(File source, File target) {
                InputStream fileIn = null;
                OutputStream fileOut = null;

                try {
                    fileIn = new BufferedInputStream(new FileInputStream(source));
                    fileOut = new BufferedOutputStream(new FileOutputStream(target));

                    byte[] buffer = new byte[1024];

                    int result;
                    while ((result = fileIn.read(buffer, 0, buffer.length)) != -1) {
                        fileOut.write(buffer, 0, result);
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

        FileCopyRunner nioBufferCopy = new FileCopyRunner() {
            @Override
            public void copyFile(File source, File target) {
                FileChannel fileIn = null;
                FileChannel fileOut = null;

                try {
                    fileIn= new FileInputStream(source).getChannel();
                    fileOut = new FileOutputStream(target).getChannel();

                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    while (fileIn.read(buffer) != -1) {
                        buffer.flip();
                        while (buffer.hasRemaining()) {
                            fileOut.write(buffer);
                        }
                        buffer.clear();
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

        FileCopyRunner noTransferCopy;
    }
}
