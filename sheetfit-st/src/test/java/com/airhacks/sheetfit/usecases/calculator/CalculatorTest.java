package com.airhacks.sheetfit.usecases.calculator;

import com.airhacks.sheetfit.ExcelReader;
import static com.airhacks.sheetfit.ExcelReader.asLong;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Function;
import java.util.stream.Stream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

/**
 *
 * @author airhacks.com
 */
@RunWith(Parameterized.class)
public class CalculatorTest {

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {"evolved", "first"},
            {"simple", "second"}
        });
    }

    //the folder
    @Parameter(0)
    public String useCase;

    //file name without the .xslx ending
    @Parameter(1)
    public String fileName;

    private Calculator cut;

    @Before
    public void init() {
        this.cut = new Calculator();
    }

    @Test
    public void calculations() {
        Stream<Input> tests = ExcelReader.load(pojoMapper(), true, 0, this.getClass(), useCase, fileName);
        tests.filter(i -> this.cut.multiply(i.getA(), i.getB()) != i.getResult()).
                map(i -> i.toString()).
                forEach(Assert::fail);
    }

    Function<Row, Input> pojoMapper() {
        return (row) -> {
            Iterator<Cell> cells = row.cellIterator();
            return new Input(
                    asLong(cells.next()),
                    asLong(cells.next()),
                    asLong(cells.next()));
        };
    }

}
