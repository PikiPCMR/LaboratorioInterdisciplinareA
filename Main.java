public class Main {
  public static void main(String[] args) throws IOException
  {
    Files.lines(path)
    .skip(1)
    .map( line -> 
    { 
      String[] fields = line.split(",");
      return new smartphone(Integer.parseInt(fields[0], fields[1], fields[2]));
    })
    .forEach(Sysyem.out::println);
  }
}
