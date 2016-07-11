package com.airhacks.sheetfit.usecases.calculator;

/**
 *
 * @author airhacks.com
 */
public class Input {

    private long a;
    private long b;
    private long result;

    public Input(long a, long b, long result) {
        this.a = a;
        this.b = b;
        this.result = result;
    }

    public long getA() {
        return a;
    }

    public long getB() {
        return b;
    }

    public long getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "Input{" + "a=" + a + ", b=" + b + ", result=" + result + '}';
    }

}
