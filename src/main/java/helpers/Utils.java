package helpers;

import pojos.CustomerAddresses;
import pojos.Customers;
import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class Utils {
    public static Customers createCustomerWithFakeData() {
        Faker faker = new Faker();
        FakeValuesService fakeValuesService = new FakeValuesService(
                new Locale("en-GB"), new RandomService());
        Customers customer = Customers.builder()
                .id(faker.number().numberBetween(30,30))
                .profile_name(faker.name().firstName())
                .email(faker.bothify("????##@gmail.com"))
                .phone(faker.numerify("##########"))
                .age(faker.random().nextInt(18,99))
                .Gdpr_consent(faker.random().nextBoolean())
                .Is_customer_profile_active(faker.random().nextBoolean())
                .Profile_created_at(Timestamp.valueOf(LocalDateTime.now()))
                .Profile_deactivated(Timestamp.valueOf(LocalDateTime.now()))
                .reason_for_deactivation("No reason")
                .notes("Some text")
                .build();
        System.out.println(customer);
        return customer;
    }
    public static List<Customers> createListOfCustomers(){
        Faker faker = new Faker();
        FakeValuesService fakeValuesService = new FakeValuesService(
                new Locale("en-GB"), new RandomService());
        ArrayList<Customers> customers = new ArrayList<>();

        for(int i = 0; i < 5; i++) {
            customers.add(Customers.builder()
                    .id(faker.number().randomDigit())
                    .profile_name(faker.name().firstName())
                    .email(faker.bothify("????##@gmail.com"))
                    .phone(faker.numerify("##########"))
                    .age(faker.random().nextInt(18,99))
                    .Gdpr_consent(faker.random().nextBoolean())
                    .Is_customer_profile_active(faker.random().nextBoolean())
                    .Profile_created_at(Timestamp.valueOf(LocalDateTime.now()))
                    .Profile_deactivated(Timestamp.valueOf(LocalDateTime.now()))
                    .reason_for_deactivation("No reason")
                    .notes("Some text")
                    .build());
        }
        System.out.println(customers);
          return customers;
    }

    public static CustomerAddresses createCustomerAddressWithFakeData() {
        Faker faker = new Faker();
        FakeValuesService fakeValuesService = new FakeValuesService(
                new Locale("en-GB"), new RandomService());
        CustomerAddresses customerAddresses = CustomerAddresses.builder()
                .address_id(faker.number().numberBetween(30,30))
                        .address(faker.address().streetAddress())
                                .city(faker.address().city())
                                        .province(faker.bothify("no province"))
                                                .state(faker.address().state())
                                                        .postal_code(faker.random().nextInt(1000))
                                                                .country(faker.address().country())
                                                                        .build();
        System.out.println(customerAddresses);
        return customerAddresses;
    }

}
