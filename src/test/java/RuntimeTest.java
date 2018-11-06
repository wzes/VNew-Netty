import org.junit.Test;

/**
 * @author Create by xuantang
 * @date on 7/10/18
 */
public class RuntimeTest {

    @Test
    public void RuntimeTests() {
        System.out.println(Runtime.getRuntime().availableProcessors());
        System.out.println(isPowerOfTwo(3));
        System.out.println(Integer.toBinaryString(-8));
        System.out.println(Integer.toBinaryString(8));
    }

    private static boolean isPowerOfTwo(int val) {
        return (val & -val) == val;
    }

}
