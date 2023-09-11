public class CPFValidator {
    public static boolean isValidCPF(String cpf) {
        if (cpf == null || cpf.length() != 11 || cpf.chars().allMatch(x -> x == cpf.charAt(0))) {
            return false;
        }

        String number = cpf.substring(0, 9);
        String verifier = cpf.substring(9, 11);

        return calculateVerifier(number).equals(verifier);
    }

    private static String calculateVerifier(String number) {
        int[] weight = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

        String tempNumber = number + "0";

        int firstVerifier = calculateDigit(tempNumber, weight);
        int secondVerifier = calculateDigit(number + firstVerifier, weight);

        return String.valueOf(firstVerifier) + secondVerifier;
    }

    private static int calculateDigit(String str, int[] weight) {
        int sum = 0;

        for (int index = str.length() - 1; index >= 0; index--) {
            int digit = Integer.parseInt(str.substring(index, index + 1));
            sum += digit * weight[weight.length - str.length() + index];
        }

        sum = 11 - sum % 11;
        return sum > 9 ? 0 : sum;
    }
}
