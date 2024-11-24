import { useState, useEffect } from "react";

interface AddEventTaskFormProps {
    isVisible: boolean;
    onClose: () => void;
    onAdd: (newTask: Omit<EventTask, "id">) => void;
    taskToEdit: EventTask | null; // New prop for task being edited
}

interface EventTask {
    id: string;
    title: string;
    description?: string;
    date: string;
}

export default function AddEventTaskForm({
                                             isVisible,
                                             onClose,
                                             onAdd,
                                             taskToEdit,
                                         }: AddEventTaskFormProps) {
    const [title, setTitle] = useState("");
    const [description, setDescription] = useState("");
    const [date, setDate] = useState("");

    // Set form values if editing a task
    useEffect(() => {
        if (taskToEdit) {
            setTitle(taskToEdit.title);
            setDescription(taskToEdit.description || "");
            setDate(taskToEdit.date);
        }
    }, [taskToEdit]);

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();

        const newEventTask = {
            title,
            description,
            date,
        };

        if (taskToEdit) {
            // If editing a task, update the task
            onAdd(newEventTask);
        } else {
            // If adding a new task, call the addTask function
            onAdd(newEventTask);
        }

        // Reset form fields after submission
        setTitle("");
        setDescription("");
        setDate("");
        onClose(); // Close the form
    };

    return (
        <div className={`popup-overlay ${isVisible ? "active" : ""}`}>
            <div
                className={`popup-form ${isVisible ? "active" : ""}`}
                onClick={(e) => e.stopPropagation()}
            >
                <h2>{taskToEdit ? "Edit Event Task" : "Add New Event Task"}</h2>
                <form onSubmit={handleSubmit}>
                    <label>
                        Title:
                        <input
                            type="text"
                            value={title}
                            onChange={(e) => setTitle(e.target.value)}
                            required
                        />
                    </label>
                    <label>
                        Description:
                        <textarea
                            value={description}
                            onChange={(e) => setDescription(e.target.value)}
                        />
                    </label>
                    <label>
                        Date:
                        <input
                            type="datetime-local"
                            value={date}
                            onChange={(e) => setDate(e.target.value)}
                            required
                        />
                    </label>
                    <button type="submit">
                        {taskToEdit ? "Update Event Task" : "Add Event Task"}
                    </button>
                </form>
                <button onClick={onClose}>Close</button>
            </div>
        </div>
    );
}
