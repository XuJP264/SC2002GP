public class PasswordChecker implements ValidPassword{
    @Override
    public boolean isValid(String password) {
        return password != null && password.matches("[a-zA-Z0-9]{6,}");
    }
}
