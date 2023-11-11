package com.cjh.myStream;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MyStream {
    private static List<Author> getAuthors() {
        //数据初始化
        Author author = new Author(1L,"蒙多",33,"一个从菜刀中明悟哲理的祖安人",null);
        Author author2 = new Author(2L,"亚拉索",15,"狂风也追逐不上他的思考速度",null);
        Author author3 = new Author(3L,"易",14,"是这个世界在限制他的思维",null);
        Author author4 = new Author(3L,"易",14,"是这个世界在限制他的思维",null);

        //书籍列表
        List<Book> books1 = new ArrayList<>();
        List<Book> books2 = new ArrayList<>();
        List<Book> books3 = new ArrayList<>();

        books1.add(new Book(1L,"刀的两侧是光明与黑暗","哲学,爱情",88,"用一把刀划分了爱恨"));
        books1.add(new Book(2L,"一个人不能死在同一把刀下","个人成长,爱情",99,"讲述如何从失败中明悟真理"));

        books2.add(new Book(3L,"那风吹不到的地方","哲学",85,"带你用思维去领略世界的尽头"));
        books2.add(new Book(3L,"那风吹不到的地方","哲学",85,"带你用思维去领略世界的尽头"));
        books2.add(new Book(4L,"吹或不吹","爱情,个人传记",56,"一个哲学家的恋爱观注定很难把他所在的时代理解"));

        books3.add(new Book(5L,"你的剑就是我的剑","爱情",56,"无法想象一个武者能对他的伴侣这么的宽容"));
        books3.add(new Book(6L,"风与剑","个人传记",100,"两个哲学家灵魂和肉体的碰撞会激起怎么样的火花呢？"));
        books3.add(new Book(6L,"风与剑","个人传记",100,"两个哲学家灵魂和肉体的碰撞会激起怎么样的火花呢？"));

        author.setBooks(books1);
        author2.setBooks(books2);
        author3.setBooks(books3);
        author4.setBooks(books3);

        List<Author> authorList = new ArrayList<>(Arrays.asList(author,author2,author3,author4));
        return authorList;
    }
    private static Author getAuthor(){
        Author author = new Author();
        author.setName("aaa");
        return author;
    }

    public static void main(String[] args) {
        List<Author> authors = getAuthors();
        // 单列集合转为stream
        // authors.stream().distinct().filter(author->author.getAge() < 18).forEach(author-> System.out.println(author.getName()));

        // 数组转为stream
        // int[] arr = {1,2,3,4,5};
        // Arrays.stream(arr).forEach(i-> System.out.println(i));

    //    双列集合转为stream
    //     Map<String, Integer> map = new HashMap<>();
    //     map.put("蜡笔小新", 19);
    //     map.put("黑子", 13);
    //     map.put("日向翔阳", 11);
    //     Stream<Map.Entry<String, Integer>> stream = map.entrySet().stream();
    //     stream.filter(entry-> entry.getValue() > 13).forEach(entry-> System.out.println(entry.getKey()));

        //  map进行计算和转换
        // authors.stream().map(author -> author.getName()).distinct().forEach(name-> System.out.println(name));

    //    sorted对流中元素进行排序，基本数据类型不能使用sorted中的Comparator，要转为包装类Integer才行，引用类型可以直接使用。
        Integer[] arr = {12,2,32,4,5, 9 ,0};
    //     Arrays.stream(arr).sorted((a, b)->a-b).forEach(i -> System.out.println(i));
    //     authors.stream().distinct().sorted((a, b)->a.getAge() - b.getAge()).forEach(author-> System.out.println(author.getName()));

        // limit 设置流最大长度，超出部分抛弃。
        // authors.stream().limit(2).forEach(author-> System.out.println(author.getName()));

        // skip跳过流中的前n个元素，返回剩下的元素
        // authors.stream().skip(1).forEach(author-> System.out.println(author.getName()));

    //    flatMap, 可以将一个对象转为多个对象作为流中的元素，可以将对象的某个List类型属性拆解开来。 这里使用的函数式接口是Function<Author, Stream<?>
    //     authors.stream().distinct().flatMap(new Function<Author, Stream<?>>() {
    //         @Override
    //         public Stream<?> apply(Author author) {
    //             return author.getBooks().stream();
    //         }
    //     }).forEach(book-> System.out.println(book));
        // 简写形式
        // authors.stream().distinct().flatMap(author -> author.getBooks().stream()).forEach(book-> System.out.println(book));

        // 终结操作forEach、count、max、min、collect
        // 这里的max, min都可以使用Comparator，这里竟然不需要filter那样转换为包装类
        // Arrays.stream(arr).max((a, b)->a-b).ifPresent(i-> System.out.println(i));
        // long count = Arrays.stream(arr).count();
        // System.out.println(count);

        // collect 将流中的元素转为List、Set、map等转换为map需要在collect中设置Collectors.toMap(Function, Function)
        // List<String> collect = authors.stream().distinct().map(author -> author.getName()).collect(Collectors.toList());
        // System.out.println(collect.size());
        // Set<String> collect1 = authors.stream().map(author -> author.getName()).collect(Collectors.toSet());
        // System.out.println(collect1.size());
        // Map<String, Integer> collect2 = authors.stream().distinct().collect(Collectors.toMap(author -> author.getName(), author -> author.getAge()));
        // System.out.println(collect2);

        // 判断是否有年龄29以上的作家，anyMatch, 传入Predicate，返回boolean值。
        // boolean b = authors.stream().anyMatch(author -> author.getAge() > 29);
        // System.out.println(b);

        // 流中元素都不符合条件 noneMatch
        // boolean b = authors.stream().noneMatch(author -> author.getAge() > 100);
        // System.out.println(b);

        // 返回流中任意元素 findAny()
        // Optional<Author> any = authors.stream().filter(author -> author.getAge() > 1).findAny();
        // any.ifPresent(i-> System.out.println(i.getName()));

        // 返回流中第一个元素 findFirst()
        // Optional<Author> first = authors.stream().filter(author -> author.getAge() > 1).findFirst();
        // first.ifPresent(i-> System.out.println(i.getName()));

        // Integer reduce = authors.stream().distinct().map(author -> author.getAge()).reduce(0, (result, element) -> result + element);
        // System.out.println(reduce);

        // reduce有两种使用方法，一种传入初始值和BinaryOperator，另一种传入BinaryOperator
        // Optional<Integer> reduce = authors.stream().distinct().map(author -> author.getAge()).reduce(new BinaryOperator<Integer>() {
        //     @Override
        //     public Integer apply(Integer integer, Integer integer2) {
        //         return integer < integer2 ? integer : integer2;
        //     }
        // });
        // System.out.println(reduce.get());

        // 使用Optional对数据进行包装，避免空指针异常，将数据存储到Optional中的value字段。
        // 采用Optional的静态方法ofNullable将数据封装为一个Optional对象，使用ifPresent判断是否存在数据，如果存在可以消费，不存在不消费。
        // Author author = getAuthor();
        // Optional<Author> author1 = Optional.ofNullable(author);
        // author1.ifPresent(author2-> System.out.println(author2.getName()));

        // 获取数据方法，get不安全获取，orElseGet安全获取。
        // System.out.println(author1.get().getName());
        // System.out.println(author1.orElseGet(() -> new Author()).getName());

        // 函数式接口 只有一个抽象方法的接口称之为函数式接口，JDK中的函数式接口都加了@FunctionalInterface进行标识。
        /*
        * 掌握以下四种常用的函数式接口：
        *
        * 消费接口，传入参数T，进行消费，不返回。
        *  public interface Consumer<T>{
        *   void accept(T);
        *  }
        *
        * 生产接口，不传参数，返回构建好的对象T
        * public interface Supplier<T>{
        *   T test();
        * }
        *
        * 计算接口，传入参数T，计算完成之后，返回R
        * public interface Function<T, R>{
        *   R apply(T t);
        * }
        *
        * 判断接口，传入参数T，返回boolean值
        * public interface Predicate<T>{
        *   boolean test(T t);
        * }
        * */

        // 方法引用，当重写方法的方法体只有一行代码时，在一些情况下，可以省略具体的方法调用，直接使用引用的方式简写。
        // 引用类的静态方法、对象的实例方法、类的实例方法、构造方法等。常用 类的静态方法、对象的实例方法、构造方法。
        // authors.stream().distinct().map(author->author.getAge()).map(age->String.valueOf(age)).forEach(age-> System.out.println(age));
        // authors.stream().distinct().map(new Function<Author, Integer>() {
        //     @Override
        //     public Integer apply(Author author) {
        //         return author.getAge();
        //     }
        // }).map(new Function<Integer, String>() {
        //     @Override
        //     public String apply(Integer integer) {
        //         return String.valueOf(integer);
        //     }
        // }).forEach(age-> System.out.println(age));
        //
        // 使用方法引用简化
        // authors.stream().distinct().map(Author::getAge).map(String::valueOf).forEach(age-> System.out.println(age));

        //    很多Stream方法都使用了泛型，涉及到的参数和返回值都是引用数据类型，如果数据量过大，会导致重复的拆箱、装箱工作浪费性能。Stream提供了专门针对基本数据类型的方法。
        //    mapToInt, mapToLong, mapToDouble等。
        // authors.stream().mapToInt(author->author.getAge()).map(age -> age+10).filter(age -> age > 10).forEach(System.out::println);
        //    并行流，有大量元素时，可以使用并行流提高效率，将任务分配给多个线程去完成。
        // 只有一个main线程在执行。
        // authors.stream().map(new Function<Author, Integer>() {
        //     @Override
        //     public Integer apply(Author author) {
        //         System.out.println(Thread.currentThread().getName());
        //         return author.getAge();
        //     }
        // }).map(age->age+10).forEach(System.out::println);

        // 多个线程处理
        // authors.parallelStream().map(new Function<Author, Integer>() {
        //     @Override
        //     public Integer apply(Author author) {
        //         System.out.println(Thread.currentThread().getName());
        //         return author.getAge();
        //     }
        // }).forEach(System.out::println);
        // 数组转为parallelStream，会有多个线程执行Function的apply方法。
        // Arrays.stream(arr).parallel().map(new Function<Integer, Integer>() {
        //     @Override
        //     public Integer apply(Integer integer) {
        //         System.out.println(Thread.currentThread().getName());
        //         return integer + 1;
        //     }
        // }).forEach(System.out::println);
    }
}
