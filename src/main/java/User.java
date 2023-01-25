import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Users")
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "telegram_id")
    private long telegram_id;

    @Column(name = "date")
    private Date date;

    @Column(name = "username")
    private String username;

    @Column(name = "last_name")
    private String last_name;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTelegram_id() {
        return telegram_id;
    }

    public void setTelegram_id(long telegram_id) {
        this.telegram_id = telegram_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User(){
    }

    public User(String name, String last_name, String username, long telegram_id, Date date){
        this.name = name;
        this.telegram_id = telegram_id;
        this.date = date;
        this.username = username;
        this.last_name = last_name;
    }
}
