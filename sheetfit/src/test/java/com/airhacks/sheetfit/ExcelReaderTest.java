package com.airhacks.sheetfit;

/*-
 * #%L
 * sheetfit
 * %%
 * Copyright (C) 2016 Adam Bien
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import static com.airhacks.sheetfit.ExcelReader.asDouble;
import static com.airhacks.sheetfit.ExcelReader.asLong;
import static com.airhacks.sheetfit.ExcelReader.asString;
import static com.airhacks.sheetfit.ExcelReader.load;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ExcelReaderTest {

    @Rule
    public ExpectedException expected = ExpectedException.none();

    private static final String USE_CASE_FOLDER = "FIRST_USE_CASE";

    @Test
    public void map() {
        int tab = 0; // first tab
        boolean sheetContainsHeaderRow = true;
        Stream<RowPojo> pojoStream = ExcelReader.load(pojoMapper(), sheetContainsHeaderRow, tab, this.getClass(), USE_CASE_FOLDER, "rowpojo");
        assertNotNull(pojoStream);
        List<RowPojo> pojos = pojoStream.collect(Collectors.toList());
        assertThat(pojos.size(), is(3));

        RowPojo actual = pojos.get(0);
        assertThat(actual.getFirst(), is("first.first"));
        assertThat(actual.getSecond(), is(2.2d));
        assertThat(actual.getThird(), is(3l));

        actual = pojos.get(1);
        assertThat(actual.getFirst(), is("second.first"));
        assertThat(actual.getSecond(), is(3.3d));
        assertThat(actual.getThird(), is(4l));

        actual = pojos.get(2);
        assertThat(actual.getFirst(), is("3"));
        assertThat(actual.getSecond(), is(4.4d));
        assertThat(actual.getThird(), is(5l));
    }

    @Test
    public void loadSecondTab() {
        int tab = 1; // second tab
        boolean sheetContainsHeaderRow = true;

        Stream<RowPojo> pojoStream = load(pojoMapper(), sheetContainsHeaderRow, tab, this.getClass(), USE_CASE_FOLDER, "rowpojo");
        assertNotNull(pojoStream);
        List<RowPojo> pojos = pojoStream.collect(Collectors.toList());
        assertThat(pojos.size(), is(2));

        RowPojo actual = pojos.get(0);
        assertThat(actual.getFirst(), is("first.second"));

    }

    @Test
    public void loadWithHeader() {
        expected.expect(IllegalArgumentException.class);
        Stream<RowPojo> pojoStream = ExcelReader.load(pojoMapper(), false, 0, this.getClass(), USE_CASE_FOLDER, "rowpojo");
        assertNotNull(pojoStream);
        pojoStream.collect(Collectors.toList());

    }

    @Test(expected = IllegalStateException.class)
    public void notExistingFile() {
        ExcelReader.load(pojoMapper(), this.getClass(), USE_CASE_FOLDER, "-does not exist-");
    }

    Function<Row, RowPojo> pojoMapper() {
        return (row) -> {
            Iterator<Cell> cells = row.cellIterator();
            return new RowPojo(
                    asString(cells.next()),
                    asDouble(cells.next()),
                    asLong(cells.next()));
        };
    }

}
