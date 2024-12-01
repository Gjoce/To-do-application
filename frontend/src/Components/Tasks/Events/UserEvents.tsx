import React, { useEffect, useState } from "react";
import "../../../EventList.css";
import Event from "./Event";
import Footer from "../../Footer.tsx";

interface EventProps {
    id: number;
    name: string;
    description?: string;
    startTime: string;
    endTime: string;
    location: string;
    maxParticipants: number;
    currentParticipants: number;
    applied?: boolean;
}

const UserEvents: React.FC = () => {
    const [events, setEvents] = useState<EventProps[]>([]);
    const [error, setError] = useState("");

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

    return (
        <div className="event-list-container">
            <h2>My  Events</h2>
            {events.length === 0 ? (
                <p>No events found.</p>
            ) : (
                <div className="event-list">
                    {events.map((event) => (
                        <Event
                            key={event.id}
                            event={event}
                            isAdmin={false} // User is not an admin
                            onDelete={() => {}} // No delete functionality for user
                            onApply={() => {}} // No apply functionality for already applied events
                            onUpdate={() => {}} // No update functionality for user
                            onViewApplicants={() => {}} // No view applicants functionality for user
                        />
                    ))}
                </div>
            )}
            <Footer />
        </div>
    );
};

export default UserEvents;
