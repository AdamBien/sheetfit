# sheetfit

Excel data loader utility for JUnit

# Usage

You need a `Function<Row,POJO>` which maps an Excel row into a POJO or your choice:
```java
  Function<Row, Input> pojoMapper() {
        return (row) -> {
            Iterator<Cell> cells = row.cellIterator();
            return new Input(
                    asLong(cells.next()),
                    asLong(cells.next()),
                    asLong(cells.next()));
        };
    }
```

Here a the `Input` POJO from this example:

```java
public class Input {

    private long a;
    private long b;
    private long result;

    public Input(long a, long b, long result) {
        this.a = a;
        this.b = b;
        this.result = result;
    }
//...
```

With this seatup you can automatically load excel sheets from folders according to the following convention: src/test/resources/{use-case}/{test-class}.

The method `com.airhacks.sheetfit.ExcelReader.load` will automatically try to find the Excel sheet and transform the rows into a `Stream` of your POJOs.

Particularly useful is the combination with parameterized tests:

```java
@RunWith(Parameterized.class)
public class CalculatorTest {

    @Parameters(name = "multiplication with {index} {0}.xslx {1}.xslx")
    public static Collection&lt;Object[]&gt; data() {
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
    public String section;

    private Calculator cut;

    @Before
    public void init() {
        this.cut = new Calculator();
    }

    @Test
    public void calculations() {
        Stream&lt;Input&gt; tests = ExcelReader.load(pojoMapper(), true, 0, this.getClass(), useCase, section);
        tests.filter(i -&gt; this.cut.multiply(i.getA(), i.getB()) != i.getResult()).
                map(i -&gt; i.toString()).
                forEach(Assert::fail);
    }

    Function&lt;Row, Input&gt; pojoMapper() {
        return (row) -&gt; {
            Iterator&lt;Cell&gt; cells = row.cellIterator();
            return new Input(
                    asLong(cells.next()),
                    asLong(cells.next()),
                    asLong(cells.next()));
        };
    }

}
```

The example was borrowed from [https://github.com/AdamBien/sheetfit/tree/master/sheetfit-st](https://github.com/AdamBien/sheetfit/tree/master/sheetfit-st)

# Installation

```xml
&lt;dependency&gt;
	&lt;groupId&gt;com.airhacks&lt;/groupId&gt;
	&lt;artifactId&gt;sheetfit&lt;/artifactId&gt;
	&lt;version&gt;0.0.1&lt;/version&gt;
&lt;/dependency&gt;
```

# You Want More?

Checkout [http://javaeetesting.com](http://javaeetesting.com) or come to MUC: [http://workshops.adam-bien.com/about-testing.htm](http://workshops.adam-bien.com/about-testing.htm)

