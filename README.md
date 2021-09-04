# taskmaster


## **Lab26**
<br>


- **Building the first android app, that have three Activities, the main one have two buttons ("`add task`" , "`all tasks`") and every button lead to another activity.**

- "`add task`": allow users to type in details about a new task, specifically a title and a body. When users click the “submit” button, show a “submitted!” label on the page.
- "`all tasks`": It have a back button and image,without any functionality.



![image description](screenshots/lab26/homePage.jpg)



<br>

## **Lab27**
<br>

**Feature Tasks**
1. **Task Detail Page**: Creating a Task Detail page. It should have a title at the top of the page, and a Lorem Ipsum description.

2. **Settings Page**: Creating a Settings page. It should allow users to enter their username and hit save.

3. **Homepage**: It contains three different buttons with hardcoded task titles. When a user taps one of the titles, it should go to the Task Detail page, and the title at the top of the page should match the task title that was tapped on the previous page. It contains also a button to visit the Settings page, and once the user has entered their username, it should display “{username}’s tasks” above the three task buttons.



![image description](screenshots/lab27/homePage.jpg)

<br>

## **Lab28**
<br>

**Feature Tasks**
- Creating a Task class. This Task should have a *title*, a *body*, and a *state*. The state should be one of “new”, “assigned”, “in progress”, or “complete”.
- Refactoring the homepage to use a **RecyclerView** for displaying Task data. This should have hardcoded Task data for now.

* **Methodology**:
    1. First step: Creating the model class "**TaskModel**".
    2. Second: Creating a blank fragment to make a single item each time.
    3. Creating the "**Adapter**" class to bind the data.
    4. Creating the RecyclerView object in the *main* activity, set the layout manager(linear layout manager) and, set the adapter for this recyclerView. 

<br>

![Home page](screenshots/lab28/homePage.jpg)


<br>

## **Lab29**
<br>

**Feature Tasks**
1. **Task Model and Room**
- Following the directions provided in the Android documentation, set up Room in your application, and modify your Task class to be an Entity.

2. **Add Task Form**
- Modify your Add Task form to save the data entered in as a Task in your local database.

3. **Homepage**
- Refactor your homepage’s RecyclerView to display all Task entities in your database.

4. **Detail Page**
- Ensure that the description and status of a tapped task are also displayed on the detail page, in addition to the title. (Note that you can accomplish this by passing along the entire Task entity, or by passing along only its ID in the intent.)

<br>

![Main Page](screenshots/lab29/mainPage.jpg)