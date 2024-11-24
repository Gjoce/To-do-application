import EventTaskCard from "./EventCard";

interface EventTask {
    id: string;
    title: string;
    description?: string;
    date: string;
}

interface EventTaskListProps {
    tasks: EventTask[];
    onDelete: (id: string) => void;
    onEdit: (id: string) => void;
}

export default function EventTaskList({
                                          tasks,
                                          onDelete,
                                          onEdit,
                                      }: EventTaskListProps) {
    return (
        <div className="event-task-list">
            {tasks.map((task) => (
                <EventTaskCard
                    key={task.id}
                    title={task.title}
                    description={task.description}
                    date={task.date}
                    onDelete={() => onDelete(task.id)}
                    onEdit={() => onEdit(task.id)}
                />
            ))}
        </div>
    );
}
