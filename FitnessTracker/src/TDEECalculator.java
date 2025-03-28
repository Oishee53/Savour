public class TDEECalculator {
    public void calculator(User user) {
        double BMR, protein, carb, fat, estimatedWeight = 0;

        if (user.getGender().equalsIgnoreCase("male")) {
            BMR = 10 * user.getWeight() + 6.25 * user.getHeight() - 5 * user.getAge() + 5;
        } else {
            BMR = 10 * user.getWeight() + 6.25 * user.getHeight() - 5 * user.getAge() - 161;
        }

        double TDEE = BMR * 1.55; // Default: moderately active
        if (user.getGoal().equalsIgnoreCase("WeightLoss")) {
            TDEE -= 500;
            estimatedWeight = (500 * 90) / 7700; // kg loss
        } else if (user.getGoal().equalsIgnoreCase("StrengthBuilding")) {
            TDEE += 400;
            estimatedWeight = (400 * 90) / 7700; // kg gain
        }

        protein = (TDEE * 30) / 100;
        fat = (TDEE * 30) / 100;
        carb = (TDEE * 40) / 100;

        // Print diet plan in table format
        System.out.println("-----------------------------------------------------------------------------");
        System.out.printf("\n| %-44s | %-10.2f | ", "Estimated Weight Change in 3 months(kg)", estimatedWeight);
        System.out.printf("\n| %-44s | %-10.2f | ", "Daily Calorie Consumption", TDEE);
        System.out.printf("\n| %-44s | %-10.2f | ", "Protein (cal)", protein);
        System.out.printf("\n| %-44s | %-10.2f | ", "Fat (cal)", fat);
        System.out.printf("\n| %-44s | %-10.2f | ", "Carbohydrates (cal)", carb);
        System.out.println("\n----------------------------------------------------------------------------");
    }
}
