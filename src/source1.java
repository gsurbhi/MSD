

/**
 * It is dummy java source file for testing clone
 *
 */

public class source1 {

    public void foo() {

        int i=0;
        i++;
        i=i+2;
        i--;
        i--;
        i++;
    }

    public void doo() {
        String s1 = "Computer Science";
        int y, x = 307;
        String s2 = s1 + " " + x;
        String s3 = s2.substring(10,17);

        System.out.println("s1: " + s1);
        System.out.println("s2: " + s2);


        if (true) {
            System.out.println("s2: " + s2);
        }
        else {
            System.out.println("s3: " + s3);
        }

        for(int i=0; i<10;i++) {

            System.out.println(i);
        }

    }

    public boolean goo(int sum) {

        foo();
        doo();
        System.out.println("Count is " + sum);
        if (sum>0)
        return true;
        else
            return false;
    }


}
