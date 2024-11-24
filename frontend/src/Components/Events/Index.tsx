import { useState, useEffect } from "react";
import NavigationBar from "../Navbar";
import AddEventTaskForm from "./AddEvents"; // Add Event Form
import EventTaskList from "./EventList"; // Event Task List
import Footer from "../Footer"; // Footer Component

interface EventTask {
    id: string;
    title: string;
    description?: string;
    date: string;
}

export default function Index() {
    const [isEventFormVisible, setIsEventFormVisible] = useState(false);
    const [tasks, setTasks] = useState<EventTask[]>([]);
    const [error, setError] = useState<string | null>(null);
    const [taskToEdit, setTaskToEdit] = useState<EventTask | null>(null);

    // Fetch tasks from the API
    useEffect(() => {
        const fetchTasks = async () => {
            try {
                const response = await fetch("http://localhost:8080/api/event-tasks");
                if (!response.ok) {
                    throw new Error("Failed to fetch tasks");
                }
                const data = await response.json();
                setTasks(data);
                setError(null); // Clear any previous errors
            } catch (error) {
                setError("Error fetching tasks. Please try again later.");
            }
        };

        fetchTasks();
    }, []);

    // Toggle form visibility
    const toggleEventFormVisibility = () => {
        setIsEventFormVisible((prev) => !prev);
    };

    // Add a new task
    const addTask = async (newTask: Omit<EventTask, "id">) => {
        try {
            const response = await fetch("http://localhost:8080/api/event-tasks", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(newTask),
            });

            if (!response.ok) {
                throw new Error("Failed to add task");
            }

            const createdTask = await response.json();
            setTasks((prevTasks) => [...prevTasks, createdTask]);
            setIsEventFormVisible(false);
            setTaskToEdit(null); // Clear task to edit after adding
            setError(null); // Clear any previous errors
        } catch (error) {
            setError("Error adding task. Please try again.");
        }
    };

    // Handle task editing
    const handleEdit = (id: string) => {
        const task = tasks.find((task) => task.id === id);
        if (task) {
            setTaskToEdit(task); // Set task to edit
            setIsEventFormVisible(true); // Show the form for editing
        }
    };

    return (
        <>
            <NavigationBar onAddTaskClick={toggleEventFormVisibility} />

            <main className="container">
                {error && <div className="alert alert-danger">{error}</div>}

                <EventTaskList
                    tasks={tasks}
                    onDelete={(id) => {
                        const updatedTasks = tasks.filter((task) => task.id !== id);
                        setTasks(updatedTasks);
                    }}
                    onEdit={handleEdit} // Use handleEdit to edit tasks
                />
            </main>

            {/* Add or Edit Event Task Form */}
            {isEventFormVisible && (
                <AddEventTaskForm
                    isVisible={isEventFormVisible}
                    onClose={toggleEventFormVisibility}
                    onAdd={addTask}
                    taskToEdit={taskToEdit} // Pass the taskToEdit to the form
                />
            )}

            <Footer />
        </>
    );
}
