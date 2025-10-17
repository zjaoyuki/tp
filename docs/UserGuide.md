---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# LittleLogBook

## Target User
LittleLogBook is designed for kindergarten teachers who need to 
manage student and parent contact information efficiently.

**Assumptions** about our target user:
1. A kindergarten teacher who is an avid user of typed user commands
(able to use Command prompt/terminal).
2. The teacher teaches multiple classes. Each class holds multiple students.
3. The teacher will also have multiple colleagues to work with.

## Product description
LittleLogBook is a **desktop app for managing contacts, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, LittleLogBook can get your contact management tasks done faster than traditional GUI apps.

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

1. Download the latest `.jar` file from [here](https://github.com/AY2526S1-CS2103T-F14B-1/tp).

1. Copy the file to the folder you want to use as the _home folder_ for your LittleLogBook.

1. **Open and navigate to your jar file using command terminal:**

   **What is `cd`?** `cd` stands for "change directory" - it's a command that lets you navigate to different folders on your computer through the command line.

   **Step-by-step instructions:**

   **For Windows users:**
   - Press `Windows key + R`, type `cmd`, and press Enter to open Command Prompt
   - Type `cd` followed by a space, then the full path to your jar file folder
   - Example: `cd C:\Users\YourName\Desktop\LittleLogBook` (replace with your actual folder path)
   - Press Enter to navigate to that folder
   - Type `java -jar littlelogbook.jar` and press Enter to run the application

   **For Mac/Linux users:**
   - Press `Cmd + Space` (Mac) or `Ctrl + Alt + T` (Linux) to open Terminal
   - Type `cd` followed by a space, then the full path to your jar file folder
   - Example: `cd /Users/YourName/Desktop/LittleLogBook` (replace with your actual folder path)
   - Press Enter to navigate to that folder
   - Type `java -jar littlelogbook.jar` and press Enter to run the application

   **Alternative method (easier for beginners):**
   - Navigate to the folder containing `littlelogbook.jar` using your file explorer
   - **Windows:** Hold Shift + Right-click in the empty space of the folder → Select "Open PowerShell window here" or "Open command window here"
   - **Mac:** Right-click in the folder → Services → New Terminal at Folder
   - **Linux:** Right-click in the folder → "Open in Terminal"
   - Type `java -jar littlelogbook.jar` and press Enter

   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `add n/John Doe p/98765432 e/john.doe@gmail.com c/student` : Adds a contact named `John Doe` with category `student` to LittleLogBook.

   * `delete n/John Doe` : Deletes the contact named `John Doe`.

   * `view n/John Doe` : Shows full details of the contact named `John Doe`.

   * `find-n John` : Searches for contacts with names containing `John`.
   
   * `find-p 8987` : Searches for contacts with phone number containing `8987`.

   * `find-t student` : Searches for contacts labelled with tag `student`.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE`, `p/PHONE n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</box>

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

### Adding a contact: `add`

Purpose: Allows teachers to create a new contact entry (student, colleague).

Format: `add n/NAME p/PHONE e/EMAIL c/CATEGORY`

**Parameters & Validation Rules:**
- **Name (n/):** Alphabetic characters, spaces, hyphens, apostrophes only. Leading/trailing spaces trimmed, multiple spaces collapsed. Case-insensitive for duplicates. Error if empty or contains numbers/symbols.
- **Phone (p/):** 8-digit Singapore numbers only. Spaces/dashes ignored. Error if not numeric, wrong length, or invalid starting digit.
- **Email (e/):** Must follow standard email format. Case-insensitive. Error if invalid format.
- **Category (c/):** Acceptable: `student`, `colleague` (case-insensitive). Error if invalid category.

**Duplicate Handling:**
Duplicate if name + phone already exist (case-insensitive). If detected, error message: `Duplicate contact detected.`

Examples:
* `add n/John Doe p/98765432 e/john.doe@gmail.com c/student`
* `add n/Mary Tan p/91234567 e/marytan@e.nut.edu c/colleague`

**Outputs:**
- Success: GUI updates contact list, message: `New <CATEGORY> added`
- Failure: Error message with reason (invalid/missing parameter, duplicate, etc.)

### Deleting a contact: `delete`

Purpose: Remove outdated or incorrect contacts.

Format: `delete n/NAME`

**Parameters & Validation Rules:**
- **Name (n/):** Same rules as Add. Case-insensitive match.

Examples:
* `delete n/John Doe`

**Outputs:**
- Success: List updates, message: `<CATEGORY> deleted`
- Failure:
  - No match → `No contact found with name John Doe`
  - Multiple matches → `Multiple contacts found. Use more details`

### Viewing contact details: `view`

Purpose: Show full information of a contact (including notes, attendance).

Format: `view n/NAME`

**Parameters & Validation Rules:**
- Name-based search, same validation as Delete.

Examples:
* `view n/John Doe`

**Outputs:**
- Success: Display detailed profile in UI panel.
- Failure:
  - Not found → `Contact not found`
  - Multiple matches → `Multiple contacts found. Use more details for name`
  - Database retrieval error → `Unable to load contact details`


**Outputs:**
- Success: List updates to show all matching contacts.
- Failure: No results → `No contacts found for "Tan"`

### Adding/Editing notes: `note`

Purpose: Store additional info (student progress, allergies, parent instructions).

Format: `note n/NAME t/NOTE_TEXT`

**Parameters & Validation Rules:**
- **Name (n/):** Same validation as above.
- **Note text (t/):** Any UTF-8 text, up to 500 chars. Leading/trailing spaces trimmed. Error if empty.

Examples:
* `note n/John Doe t/Allergic to peanuts`
* `note n/Mary Tan t/Improved in reading this week`

**Outputs:**
- Success: Note added to contact, message: `<CATEGORY> DETAILS edited`
- Failure:
  - No match → `No contact found`
  - Empty note → `Note text cannot be empty`
  - Database save failure → `Unable to save note. Try again`

### Finding contacts by name : `find-n`
Purpose: Allows teachers to find contacts quickly with partial names(contiguous).

Format: `find-n KEYWORD`

**Parameters:**
- **Keyword:** Alphanumeric string, case-insensitive, matches partial names. Error if empty string.

Examples:
* `find-n John ecka`
* `find-n Tan`

**Outputs:**
- Success: The find-n results in matches: `<x> persons listed!`
- Failure:
    - No match → `0 persons listed!`
    - Empty string → `Invalid command format!
        find-n: Finds all persons whose names contain any of the specified keywords (case-insensitive) and 
        displays them as a list with index numbers.
        Parameters: KEYWORD [MORE_KEYWORDS]...
        Example: find-n alice bob charlie`

### Finding contacts by phone number : `find-p`
Purpose: Allows teachers to find contacts quickly with partial number(contiguous).

Format: `find-p KEYWORD`

**Parameters:**
- **Keyword:** numeric string, matches partial numbers. Error if empty string.

Examples:
* `find-p 8431 967`
* `find-p 84313390`
* `find-p 3133`

**Outputs:**
- Success: The find-p results in matches: `<x> persons listed!`
- Failure:
    - No match → `0 persons listed!`
    - Empty string → `Invalid command format! 
        find-p: Finds all persons whose phone number contain any of the specified keywords and displays them as 
        a list with index numbers.
        Parameters: KEYWORD [MORE_KEYWORDS]...
        Example: find-p 84123578`  

### Finding contacts by tags : `find-t`
Purpose: Allows teachers to find contacts quickly with tags(contiguous).

Format: `find-t KEYWORD`

**Parameters:**
- **Keyword:** alphanumeric string, matches partial tag. Error if empty string.

Examples:
* `find-t student`
* `find-t stu colle`
* `find-t ague`

**Outputs:**
- Success: The find-t results in matches: `<x> persons listed!`
- Failure:
    - No match → `0 persons listed!`
    - Empty string → `Invalid command format! 
        find-t: Finds all persons whose tag contain any of the specified keywords (case-insensitive) and displays them as a list with index numbers.
        Parameters: KEYWORD [MORE_KEYWORDS]...
        Example: find-t friend colleague`

### Listing all contacts : `list`

Shows a list of all contacts in LittleLogBook.

Format: `list`

### Clearing all entries : `clear`

Clears all entries from LittleLogBook.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

LittleLogBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

LittleLogBook data are saved automatically as a JSON file `[JAR file location]/data/littlelogbook.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, LittleLogBook will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause LittleLogBook to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous LittleLogBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action     | Format, Examples
-----------|-----------------------------------------------------------------------------------------------------------------------------------
**Add**    | `add n/NAME p/PHONE e/EMAIL c/CATEGORY`<br>e.g., `add n/John Doe p/98765432 e/john.doe@gmail.com c/student`
**Delete** | `delete n/NAME`<br>e.g., `delete n/John Doe`
**View**   | `view n/NAME`<br>e.g., `view n/John Doe`
**Find-n** | `find-n KEYWORD`<br>e.g., `find-n John`
**Find-p** | `find-p KEYWORD`<br>e.g., `find-p 84871234`
**Find-t** | `find-t KEYWORD`<br>e.g., `find-t student`
**Note**   | `note n/NAME t/NOTE_TEXT`<br>e.g., `note n/John Doe t/Allergic to peanuts`
**List**   | `list`
**Clear**  | `clear`
**Help**   | `help`
**Exit**   | `exit`
