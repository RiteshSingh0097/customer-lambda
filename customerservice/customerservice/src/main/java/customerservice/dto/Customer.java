package customerservice.dto;


public class Customer {
    private int id;
    private String firstName;
    private String lastName;
    private int rewardPoints;

    public Customer() {
    }

    public Customer(int id, String firstName, String lastName, int rewardPoints) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.rewardPoints = rewardPoints;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getRewardPoints() {
        return rewardPoints;
    }

    public void setRewardPoints(int rewardPoints) {
        this.rewardPoints = rewardPoints;
    }
}
