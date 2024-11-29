import React, { useEffect, useState } from "react";

interface EventProps {
    id: number;
    name: string;
    description?: string;
    startTime: string;
    endTime: string;
    location: string;
    maxParticipants: number;
}

const ApplyEvent: React.FC = () => {
    const [events, setEvents] = useState<EventProps[]>([]);

    useEffect(() => {
        const fetchEvents = async () => {
            try {
                const response = await fetch("http://localhost:8080/events");
                if (!response.ok) throw new Error("Failed to fetch events");
                const data = await response.json();
                setEvents(data);
            } catch (err) {
                console.error(err);
            }
        };
        fetchEvents();
    }, []);

    const handleApply = async (eventId: number) => {
        const userId = localStorage.getItem("userId");
        try {
            const response = await fetch(`http://localhost:8080/events/${eventId}/apply`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ userId }),
            });
            if (response.ok) alert("Successfully applied to event!");
            else alert("Failed to apply to event.");
        } catch (err) {
            console.error(err);
        }
    };

    return (
        <div className="apply-event-container">
            <h2>Our Available Events</h2>
            {events.map((event) => (
                <div key={event.id} className="event-card">
                    <h3>{event.name}</h3>
                    <p>{event.description}</p>
                    <p>
                        <strong>Location:</strong> {event.location}
                    </p>
                    <button onClick={() => handleApply(event.id)}>Apply</button>
                </div>
            ))}
        </div>
    );
};

export default ApplyEvent;
