

/**
 * It is dummy java source file for testing clone
 *
 */

public class source2 {
    public void bar() {

        int j=0;
        j++;
        j=j+2;
        j--;
        j--;
        j++;
    }

    public boolean goo(int sum) {

        bar();
        loo();
        System.out.println("Sum is " + sum);
        if (sum>0)
            return true;
        else
            return false;
    }

    public void loo() {
        String v1 = "Computer Science";
        int y;
        int a = 307;
        String v2 = v1 + " " + a;
        String v3 = v2.substring(10,17);

        /**
         * Check the condition
         */

        if (true) {
            System.out.println("s2: " + v2);
        }
        else {
            System.out.println("s3: " + v3);
        }

        for(int index=0; index<10;index++) {
            System.out.println(index);
        }
    }
}
