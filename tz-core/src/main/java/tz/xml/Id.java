package tz.xml;

/**
 * @author Dmitry Shyshkin
 */
public class Id {
    private long number;

    private int server;

    public Id(long number, int server) {
        this.number = number;
        this.server = server;
    }

    public Id(String id) {
        int index = id.indexOf('.');
        number = Long.parseLong(id.substring(0, index));
        server = Integer.parseInt(id.substring(index + 1));
    }

    public long getNumber() {
        return number;
    }

    public int getServer() {
        return server;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Id id = (Id) o;

        return number == id.number && server == id.server;
    }

    @Override
    public int hashCode() {
        int result = (int) (number ^ (number >>> 32));
        result = 31 * result + server;
        return result;
    }

    @Override
    public String toString() {
        return number + "." + server;
    }
}
