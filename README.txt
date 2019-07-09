@author Alexey Sushko.

Application 'Database migration platform'.

This application is designed to read database metadata and display it in a tree
structure.
In the application, there are three types of tree node loading: lazy, details, full.

Lazy - downloads a list of children selected item.<br/>
Details - loads the attributes of the element.<br/>
Full - recursively loads all children with attributes.<br/>

By clicking on the title of the element, the attributes of the element are displayed
in the form
of a table in the central part of the screen and the DDL is generated.
DDL has the ability to save to a file.
There is also the possibility of searching for elements by the key and value of
attributes and
highlighting of the elements found in the tree.
The page state is saved at the time of the session.
