import React, { useState } from "react";

const AddEvent = () => {
  const [event, setEvent] = useState({
    name: "",
    description: "",
    startTime: "",
    endTime: "",
    location: "",
    maxParticipants: 1,
  });

  const handleChange = (e) => {
    setEvent({ ...event, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const userId = localStorage.getItem("userId");
    const response = await fetch(`/events?userId=${userId}`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(event),
    });
    if (response.ok) {
      alert("Event added successfully!");
    } else {
      alert("Failed to add event.");
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <input name="name" placeholder="Name" onChange={handleChange} required />
      <textarea
        name="description"
        placeholder="Description"
        onChange={handleChange}
      />
      <input
        name="startTime"
        type="datetime-local"
        onChange={handleChange}
        required
      />
      <input
        name="endTime"
        type="datetime-local"
        onChange={handleChange}
        required
      />
      <input
        name="location"
        placeholder="Location"
        onChange={handleChange}
        required
      />
      <input
        name="maxParticipants"
        type="number"
        min="1"
        onChange={handleChange}
        required
      />
      <button type="submit">Add Event</button>
    </form>
  );
};

export default AddEvent;
