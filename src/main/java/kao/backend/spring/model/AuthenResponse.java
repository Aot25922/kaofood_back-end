package kao.backend.spring.model;

public class AuthenResponse {
    private final String jwt;

    public AuthenResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
}
