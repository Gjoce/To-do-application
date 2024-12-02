import React, { useEffect, useState } from "react";
import isAdmin from "./Index";

interface EventProps {
    id: number;
    name: string;
    description?: string;
    startTime: string;
    endTime: string;
    location: string;
    maxParticipants: number;
    applied?: boolean;
    remainingSlots?: number;
}

const ApplyEvent: React.FC = () => {
    const [events, setEvents] = useState([]);
    const [selectedEvents, setSelectedEvents] = useState<Set<number>>(new Set());
    const [loading, setLoading] = useState(false);

    const fetchEvents = async () => {
        setLoading(true);
        try {
            const response = await fetch("http://localhost:8080/events");
            if (!response.ok) throw new Error("Failed to fetch events");
            const data = await response.json();
            setEvents(data);
        } catch (err) {
            console.error("Error fetching events:", err);
        } finally {
            setLoading(false);
        }
    };

    const applyToSelectedEvents = async () => {
        const userId = localStorage.getItem("userId");
        if (!userId) {
            alert("User ID not found. Please log in.");
            return;
        }
        setLoading(true);
        try {
            for (const eventId of selectedEvents) {
                const response = await fetch(`http://localhost:8080/events/${eventId}/apply?userId=${userId}`, {
                    method: "POST",
                });
                if (!response.ok) throw new Error(`Failed to apply to event with ID: ${eventId}`);
                const updatedEvent = await response.json();

                setEvents((prev) =>
                    prev.map((event) => (event.id === eventId ? { ...event, ...updatedEvent, applied: true } : event))
                );
            }
            setSelectedEvents(new Set());
            alert("Successfully applied to selected events.");
        } catch (err) {
            console.error("Error applying to events:", err);
            alert("Failed to apply to some or all events.");
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchEvents();
    }, []);

    return (
        <div className="apply-event-container">
            <h2>Available Events</h2>
            {loading ? (
                <p>Loading events...</p>
            ) : (
                events.map((event) => (
                    <div key={event.id} className="event-card">
                        <h3>{event.name}</h3>
                        <p>{event.description || "No description available"}</p>
                        <p>
                            <strong>Start Time:</strong> {new Date(event.startTime).toLocaleString()}
                        </p>
                        <p>
                            <strong>End Time:</strong> {new Date(event.endTime).toLocaleString()}
                        </p>
                        <p>
                            <strong>Location:</strong> {event.location}
                        </p>
                        <p>
                            <strong>Max Participants:</strong> {event.maxParticipants}
                        </p>
                        {event.applied && event.remainingSlots !== undefined && (
                            <p>
                                <strong>Remaining Slots:</strong> {event.remainingSlots}
                            </p>
                        )}
                        {!isAdmin() && (
                            <div>
                                <input
                                    type="checkbox"
                                    disabled={event.applied}
                                    checked={selectedEvents.has(event.id)}
                                    onChange={() =>
                                        setSelectedEvents((prev) =>
                                            prev.has(event.id) ? new Set([...prev].filter((id) => id !== event.id)) : new Set([...prev, event.id])
                                        )
                                    }
                                />
                                {event.applied && <span style={{ color: "green", marginLeft: "10px" }}>Applied</span>}
                            </div>
                        )}
                    </div>
                ))
            )}
            {!isAdmin() && selectedEvents.size > 0 && (
                <button className="apply-btn" onClick={applyToSelectedEvents} disabled={loading}>
                    Apply to Selected Events
                </button>
            )}
        </div>
    );
};

export default ApplyEvent;
