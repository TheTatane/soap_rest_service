package dom;

public class AlbumType {

    private String title;
    private String id;

    @Override
    public String toString() {
        return "AlbumType{" +
                "title='" + title + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    public AlbumType()
    {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
