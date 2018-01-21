package shrikant.com.upcomingmovie.Config;

/**
 * Created by ShrikantIngole on 21-01-2018.
 */

public class Config
{
    private String UpcomingMovieList="https://api.themoviedb.org/3/movie/upcoming";

    public String getApiKey() {
        return ApiKey;
    }

    private String ApiKey="b7cd3340a794e5a2f35e3abb820b497f";

    public String getPosterImages() {
        return PosterImages;
    }

    private String PosterImages="https://image.tmdb.org/t/p/w300/";

    public String getUpcomingMovieList() {
        return UpcomingMovieList;
    }
}
