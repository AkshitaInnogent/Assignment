import './App.css';
import React, { useState, useEffect } from 'react';
import TaskList from './TaskList';
import Header from './Header';
import AddTask from './AddTask';

function App() {
  const LOCAL_STORAGE_KEY = "tasks";

  const [todolist, setTodolist] = useState(
    JSON.parse(localStorage.getItem(LOCAL_STORAGE_KEY)) || []
  );

  const addTaskHandler = (task) => {
    const newTask = { id: Date.now(), task: task.task };
    const newTasks = [...todolist, newTask];
    setTodolist(newTasks);
    localStorage.setItem(LOCAL_STORAGE_KEY, JSON.stringify(newTasks));
  };

  const deleteTaskHandler = (id) => {
    const updatedTasks = todolist.filter((task) => task.id !== id);
    setTodolist(updatedTasks);
    localStorage.setItem(LOCAL_STORAGE_KEY, JSON.stringify(updatedTasks));
  };

  const updateTaskHandler = (id, updatedText) => {
    const updatedTasks = todolist.map((task) =>
      task.id === id ? { ...task, task: updatedText } : task
    );
    setTodolist(updatedTasks);
    localStorage.setItem(LOCAL_STORAGE_KEY, JSON.stringify(updatedTasks));
  };

  useEffect(() => {
    const retrieveTasks = JSON.parse(localStorage.getItem(LOCAL_STORAGE_KEY));
    if (retrieveTasks) setTodolist(retrieveTasks);
  }, []);

  return (
    <div className="app-container">
      <Header />
      <AddTask addTaskHandler={addTaskHandler} />
      <TaskList
        todolist={todolist}
        deleteTaskHandler={deleteTaskHandler}
        updateTaskHandler={updateTaskHandler}
      />
    </div>
  );
}

export default App;