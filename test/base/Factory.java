package base;

public interface Factory {
    Object getBean(String var1);

    <T> T getBean(String var1, Class<T> var2);

    <T> T getBean(Class<T> var1);
}
