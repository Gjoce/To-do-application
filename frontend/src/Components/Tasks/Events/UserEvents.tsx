import React, { useEffect, useState } from "react";
import "../../../EventList.css";
import Footer from "../../Footer.tsx";
import EventDetails from "./EventDetails";
import { useNavigate } from "react-router-dom";

interface EventProps {
    id: number;
    name: string;
    description?: string;
    startTime: string;
    endTime: string;
    location: string;
    maxParticipants: number;
    applied?: boolean;
}

const UserEvents: React.FC = () => {
    const [events, setEvents] = useState<EventProps[]>([]);
    const [error, setError] = useState("");
    const navigate = useNavigate(); // Initialize navigate hook

    const fetchUserEvents = async () => {
        const userId = localStorage.getItem("userId");
        if (!userId) {
            setError("User not logged in.");
            return;
        }

        try {
            const response = await fetch(`http://localhost:8080/events/users/${userId}/events`);
            if (!response.ok) throw new Error("Failed to fetch user-applied events.");
            const data: EventProps[] = await response.json();
            setEvents(data);
        } catch (err) {
            console.error("Error fetching user events:", err);
            setError("Failed to fetch user-applied events.");
        }
    };

    useEffect(() => {
        fetchUserEvents();
    }, []);

    if (error) {
        return <p>{error}</p>;
    }

    // Function to handle navigating back to the events list
    const handleBacktoEvents = () => {
        navigate("/events");
    };

    return (
        <div className="event-list-container">
            <h2>My Applied Events</h2>
            {events.length === 0 ? (
                <p>No events found.</p>
            ) : (
                <div className="event-list">
                    {events.map((event) => (
                        <EventDetails
                            key={event.id}
                            id={event.id}
                            name={event.name}
                            description={event.description}
                            startTime={event.startTime}
                            endTime={event.endTime}
                            location={event.location}
                            maxParticipants={event.maxParticipants}
                        />
                    ))}
                </div>
            )}
            <div className="back-to-events-button-container">
                <button
                    className="back-to-tasks-btn"
                    onClick={handleBacktoEvents}
                >
                    Back to Events
                </button>
            </div>
            <Footer />
        </div>
    );
};

export default UserEvents;
