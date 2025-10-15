// Deep investigation of the Name normalization process
import seedu.address.model.person.Name;
import java.lang.reflect.Method;

class DeepNameInvestigation {
    public static void main(String[] args) {
        System.out.println("=== Deep Name Investigation ===");
        
        try {
            // Test the normalizeName method using reflection
            Class<?> nameClass = Name.class;
            Method normalizeMethod = nameClass.getDeclaredMethod("normalizeName", String.class);
            normalizeMethod.setAccessible(true);
            
            String result1 = (String) normalizeMethod.invoke(null, "Valid Name");
            String result2 = (String) normalizeMethod.invoke(null, "valid name");
            
            System.out.println("normalizeName('Valid Name'): '" + result1 + "'");
            System.out.println("normalizeName('valid name'): '" + result2 + "'");
            System.out.println("Are normalized results equal? " + result1.equals(result2));
            
        } catch (Exception e) {
            System.out.println("Error accessing normalizeName: " + e.getMessage());
        }
        
        // Test with exact same strings as the failing test
        Name name1 = new Name("Valid Name");
        Name name2 = new Name("valid name");
        
        System.out.println("\n=== Exact Test Scenario ===");
        System.out.println("name1 fullName: '" + name1.fullName + "'");
        System.out.println("name2 fullName: '" + name2.fullName + "'");
        System.out.println("name1.equals(name2): " + name1.equals(name2));
        
        // Test the exact assertion that's failing
        boolean result = name1.equals(name2);
        System.out.println("\nThe failing assertion: assertFalse(name.equals(new Name(\"valid name\")))");
        System.out.println("Actual result: " + result);
        System.out.println("Expected: false");
        System.out.println("Would assertion pass? " + (!result));
    }
}
