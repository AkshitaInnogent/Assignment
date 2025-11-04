import React, { useState } from "react";

const AddTask = ({ addTaskHandler }) => {
  const [task, setTask] = useState("");

  const add = (e) => {
    e.preventDefault();
    if (task === "") {
      alert("All fields are mandatory!");
      return;
    }

    addTaskHandler({ task });
    setTask("");
  };

  return (
    <div className="add-task">
      <form onSubmit={add}>
        <div>
          <label>Task : </label>
          <input
            type="text"
            name="task"
            placeholder="Enter your task"
            value={task}
            onChange={(e) => setTask(e.target.value)}
          />
        </div>
        <button type="submit">Add Task</button>
      </form>
    </div>
  );
};

export default AddTask;