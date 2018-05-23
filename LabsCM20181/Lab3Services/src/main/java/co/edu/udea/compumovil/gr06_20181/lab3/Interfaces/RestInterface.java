package co.edu.udea.compumovil.gr06_20181.lab3.Interfaces;

import co.edu.udea.compumovil.gr06_20181.lab3.POJO.Drink;
import co.edu.udea.compumovil.gr06_20181.lab3.POJO.Message;
import co.edu.udea.compumovil.gr06_20181.lab3.POJO.Plate;
import co.edu.udea.compumovil.gr06_20181.lab3.POJO.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface RestInterface {

    String URL_LOGIN = "http://192.168.1.136:5000/login/";
    String URL_SINGUP = "http://192.168.1.136:5000/singup/";
    String URL_USER = "http://192.168.1.136:5000/data/user/";
    String URL_PLATES = "http://192.168.1.136:5000/data/plates/";
    String URL_DRINKS = "http://192.168.1.136:5000/data/drinks/";


    @Headers("Content-Type: application/json")
    @POST("/login")
    Call<Message> loginUser(@Body String login);

    @Headers("Content-Type: application/json")
    @POST("/data/user")
    Call<User> findUser(@Body String email);

    @Headers("Content-Type: application/json")
    @POST("/singup")
    Call<Message> singupUser(@Body User user);

    @Headers("Content-Type: application/json")
    @POST("/data/plates")
    Call<Message> savePlate(@Body Plate plate);

    @Headers("Content-Type: application/json")
    @POST("/data/drinks")
    Call<Message> saveDrink(@Body Drink drink);
}
