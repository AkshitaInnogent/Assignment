import React, { useState } from 'react';

const OneTask = ({ task, deleteTaskHandler, updateTaskHandler }) => {
  const { id, task: text } = task;
  const [isEditing, setIsEditing] = useState(false);
  const [updatedText, setUpdatedText] = useState(text);

  const handleUpdate = (e) => {
    e.preventDefault();
    if (typeof updateTaskHandler === "function") {
      updateTaskHandler(id, updatedText);
    } else {
      console.error("updateTaskHandler is not a function");
    }
    setIsEditing(false);
  };

  return (
    <div className="task-item">
      {isEditing ? (
        <form className="edit-form" onSubmit={handleUpdate}>
          <input
            type="text"
            value={updatedText}
            onChange={(e) => setUpdatedText(e.target.value)}
          />
          <button type="submit" className="save-btn">Save</button>
          <button type="button" className="cancel-btn" onClick={() => setIsEditing(false)}>
            Cancel
          </button>
        </form>
      ) : (
        <div className="task-content">
          <div className="task-header">{text} </div>
          <div className="task-buttons">
            <button className="edit-btn" onClick={() => setIsEditing(true)}>Edit</button>
            <button className="delete-btn" onClick={() => deleteTaskHandler(id)}>Delete</button>
          </div>
        </div>
      )}
    </div>
  );
};

export default OneTask;