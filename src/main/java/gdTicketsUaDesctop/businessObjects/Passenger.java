package gdTicketsUaDesctop.businessObjects;

/**
 * @author Nazar on 03.11.2016.
 */
public class Passenger {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String tea;
    private String noBed;
    private String cargo;
    private Passenger builder;

//    private Passenger(PassengerBuilder builder) {
//        this.firstName = builder.firstName;
//        this.lastName = builder.lastName;
//        this.email = builder.email;
//        if (email.startsWith("@")) email = String.valueOf(System.currentTimeMillis()) + email;
//        this.phone = builder.phone;
//        this.tea = builder.tea;
//        this.noBed = builder.noBed;
//        this.cargo = builder.cargo;
//    }

    private Passenger(){}

    @Override
    public String toString() {
        return "Passenger {" +
                "firstName='" + firstName + '\'' +
                "lastName='" + lastName + '\'' +
                "email='" + email + '\'' +
                "phone='" + phone + '\'' +
                "tea='" + tea + '\'' +
                "noBed='" + noBed + '\'' +
                "cargo='" + cargo + '\'' +
                '}';
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getTea() {
        return tea;
    }

    public String getNoBed() {
        return noBed;
    }

    public String getCargo() {
        return cargo;
    }

    public PassengerBuilder rebuild(){
        PassengerBuilder builder = new PassengerBuilder();
        builder.passenger = this;
        return builder;
    }


    public static class PassengerBuilder {
        private Passenger passenger;
//        private String firstName;
//        private String lastName;
//        private String email;
//        private String phone;
//        private String tea;
//        private String noBed;
//        private String cargo;

        public PassengerBuilder(){
            this.passenger = new Passenger();

        }

        public PassengerBuilder withFirstName(String value) {
            this.passenger.firstName = value;
            return this;
        }

        public PassengerBuilder withLastName(String value) {
            this.passenger.lastName = value;
            return this;
        }

        public PassengerBuilder withEmail(String value) {
            this.passenger.email = value;
            return this;
        }

        public PassengerBuilder withPhone(String value) {
            this.passenger.phone = value;
            return this;
        }

        public PassengerBuilder withTea(String value) {
            this.passenger.tea = value;
            return this;
        }
//
        public PassengerBuilder withNoBed(String value) {
            this.passenger.noBed = value;
            return this;
        }

        public PassengerBuilder withCargo(String value) {
            this.passenger.cargo = value;
            return this;
        }

//        public PassengerBuilder rebuild(Passenger passenger){
//            this.passenger = passenger;
//            return this;
//        }

        public Passenger build() {
//            return new Passenger(this);

            return this.passenger;
        }

        public void finish(){}

    }
}


