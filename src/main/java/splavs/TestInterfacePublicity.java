package splavs;

import splavs.testinterface.PublicInterface;

/**
 * Class description goes here.
 *
 * @author Vyacheslav Silchenko
 */
public class TestInterfacePublicity implements PublicInterface{
    @Override
    public void test() {
        System.out.println(PublicInterface.value);

    }

}
