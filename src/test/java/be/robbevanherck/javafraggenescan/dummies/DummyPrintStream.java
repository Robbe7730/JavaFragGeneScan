package be.robbevanherck.javafraggenescan.dummies;

import java.io.OutputStream;
import java.io.PrintStream;

public class DummyPrintStream extends PrintStream {
    String result;
    public DummyPrintStream() {
        super(OutputStream.nullOutputStream());
        this.result = "";
    }

    @Override
    public PrintStream append(CharSequence csq) {
        result += csq.toString();
        return this;
    }

    @Override
    public PrintStream append(char c) {
        result += c;
        return this;
    }

    public String getResult() {
        return result;
    }

    public void clear() {
        result = "";
    }
}
