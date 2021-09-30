package co.tiagoaguiar.evernote;

/**
 * Julho, 31 2019
 *
 * @author suporte@moonjava.com.br (Tiago Aguiar).
 */
public class Note {

  private int id;
  private String title;
  private String desc;
  private String date;
  private String body;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }
}
