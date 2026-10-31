# Library Management System (Java)

A console-based library system written in Java. Members (students, faculty and the public) can register, log in, browse the catalogue and view their loans and fines. Admins manage books, admin accounts, loans and fines. All data is saved to plain text files, so no database is needed.

## Features

### Members
- Register as a **Student**, **Faculty** or **Public** member, with an ID that starts with `STU`, `FAC` or `PUB`
- Log in with a 3-attempt limit on the password
- View profile (role, borrow period, registration date, expiry date, membership status)
- Change password (minimum 6 characters) or delete own account
- Browse the catalogue: view all books, search by ID, title, author or category, borrow and return
- View personal loans and fines

### Admins
- Log in (3 attempts) or register a new admin (up to 10 admins)
- View, update and delete admin accounts, and view personal details
- **Manage books:** view, add, search, update and delete
- **Manage loans and fines:** view loans and fines, pay a fine, delete a loan, delete an unpaid fine, and extend a due date

## Membership Rules

| Member type | ID prefix | Loan period | Membership validity |
|-------------|-----------|-------------|---------------------|
| Student | `STU` | 14 days | 4 years |
| Faculty | `FAC` | 30 days | 10 years |
| Public | `PUB` | 7 days | 1 year |

- **Fine:** RM 1.00 per day late, created when an overdue loan is returned.
- **Extensions:** an admin can extend a due date by 1 to 30 days, up to 30 extra days in total per loan.
- **Fine payment:** Cash, Online or Card. A paid fine cannot be deleted.
- **Membership status** is shown in the profile as `ACTIVE`, `EXPIRING SOON` (30 days or fewer left) or `EXPIRED`.

## Book IDs

IDs are generated automatically from the book's category.

| Category | Prefix | Example |
|----------|--------|---------|
| Fantasy | `F` | F001 |
| History | `H` | H001 |
| Action | `A` | A001 |
| Programming | `P` | P001 |
| Fiction | `FI` | FI001 |
| Science | `S` | S001 |
| Biography | `B` | B001 |
| Other | `G` | G001 |

The catalogue holds up to 100 books. Changing a book's category gives it a new ID.

## Project Structure

| File | Purpose |
|------|---------|
| `LibrarySystem.java` | Entry point (`main`). Loads data and shows the Member / Admin / Exit menu. |
| `MemberSystem.java`, `LoginManager.java`, `RegistrationManager.java` | Member login and registration flow. |
| `BaseRegistration.java`, `StudentRegistration.java`, `FacultyRegistration.java`, `PublicRegistration.java` | Registration for each member type. |
| `User.java`, `Student.java`, `Faculty.java`, `PublicMember.java` | User model and member types, each with its own loan period and expiry. |
| `UserMenu.java`, `MemberMenu.java` | Menus shown after a member logs in. |
| `Admin.java`, `AdminManager.java`, `StaffMenu.java` | Admin accounts, admin menus, and loan and fine management. |
| `LibraryItem.java`, `Book.java`, `catalogue.java` | Book model and catalogue operations (add, search, update, delete, borrow, return). |
| `Record.java`, `Loan.java`, `Fine.java` | Base record class, loans (`L001`) and fines (`F001`). |
| `LoanManager.java`, `FineManager.java` | Create, view, extend, delete and save loans and fines. |
| `FileHandler.java` | Reads and writes member data files. |
| `Library.java` | Library class. |

## Data Files

Data is stored as text files in the project folder and loaded at start-up.

| File | Content | Format |
|------|---------|--------|
| `books.txt` | Book catalogue | `title\|id\|author\|category\|publisher\|year\|available` |
| `students.txt`, `faculties.txt`, `public_member.txt` | Member accounts | `\|`-separated fields, ending with the registration date |
| `admins.txt` | Admin accounts | `name\|adminID\|password\|salary` |
| `loans.txt` | Loans | comma-separated |
| `fines.txt` | Fines | comma-separated |

> The program reads these files using relative paths, so **run it from the project folder**.

## Requirements

- JDK 8 or later
- No external libraries

## How to Run

```bash
git clone https://github.com/cherylsryan/LibraryManagementSystem_Java.git
cd LibraryManagementSystem_Java
javac *.java
java LibrarySystem
```

You can also open the folder in an IDE such as NetBeans, IntelliJ or Eclipse and run `LibrarySystem.java`.

## Object-Oriented Design

- **Inheritance:** `Student`, `Faculty` and `PublicMember` extend `User`. `Book` extends `LibraryItem`. `Loan` and `Fine` extend `Record`. The registration classes extend `BaseRegistration`.
- **Polymorphism:** each member type overrides its own loan period (`getMaxBorrowDays`) and expiry date, and `Loan` and `Fine` override `toString()`.
- **Separation of concerns:** menus handle input and output, manager classes hold the business logic, and `FileHandler` and the `save` / `load` methods handle storage.

## Known Limitations

- Passwords are stored in plain text in the data files.
- In the member menus, borrowing and returning a book only changes the book's availability in the catalogue. Loan records are created through `LoanManager`, so check that it is called from the borrow flow if you want loans and fines to appear automatically.
- Book IDs in `books.txt` should be unique. Duplicates make lookups return only the first match.
- The catalogue (100 books) and admin list (10 admins) have fixed sizes.



