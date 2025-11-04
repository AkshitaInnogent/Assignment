import React from "react";
import OneTask from "./OneTask";

const TaskList = ({ todolist, deleteTaskHandler, updateTaskHandler }) => {
  return (
    <div className="task-list">
      {todolist.map((task) => (
        <OneTask
          key={task.id}
          task={task}
          deleteTaskHandler={deleteTaskHandler}
          updateTaskHandler={updateTaskHandler}
        />
      ))}
    </div>
  );
};

export default TaskList;