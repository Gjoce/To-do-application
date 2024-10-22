document.addEventListener('DOMContentLoaded', () => {

    fetchTasks('all');


    const filterButtons = document.querySelectorAll('.filter-btn');
    filterButtons.forEach(button => {
        button.addEventListener('click', () => {
            const status = button.getAttribute('data-filter');
            fetchTasks(status);
        });
    });


    const form = document.getElementById('addTaskForm');
    form.addEventListener('submit', (event) => {
        event.preventDefault();
        const taskData = {
            title: document.getElementById('title').value,
            description: document.getElementById('description').value,
            status: document.getElementById('status').value,
            priority: document.getElementById('priority').value,
            dueDate: document.getElementById('dueDate').value
        };
        createTask(taskData);
    });
});


function fetchTasks(status) {

    const statusEnumMap = {
        all: null,
        completed: "FINISHED",
        pending: "PENDING"

    };

    const enumStatus = statusEnumMap[status];
    let url = 'http://localhost:8080/api/tasks';
    if (enumStatus) {
        url += `?status=${enumStatus}`;
    }

    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            displayTasks(data);
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
        });
}

function displayTasks(tasks) {
    const taskList = document.getElementById('taskList');
    taskList.innerHTML = '';

    tasks.forEach(task => {
        const taskCard = document.createElement('div');
        taskCard.className = 'card mb-3';

        taskCard.innerHTML = `
            <div class="card-header d-flex justify-content-between align-items-center">
                <strong>${task.title}</strong>
                <span class="badge bg-${getBadgeClass(task.priority)}">${task.priority}</span>
            </div>
            <div class="card-body">
                <p class="card-text"><strong>Description:</strong> ${task.description || 'No description provided.'}</p>
                <p class="card-text"><strong>Status:</strong> ${task.status}</p>
                <p class="card-text"><strong>Due Date:</strong> ${formatDueDate(task.dueDate)}</p>
                <div class="d-flex justify-content-between">
                    <button class="btn btn-success complete-btn">Complete</button>
                    <button class="btn btn-danger delete-btn">Delete</button>
                </div>
            </div>
        `;

        taskList.appendChild(taskCard);


        const completeButton = taskCard.querySelector('.complete-btn');
        completeButton.addEventListener('click', () => {
            updateTaskStatus(task.id, 'FINISHED', taskCard);
        });

        const deleteButton = taskCard.querySelector('.delete-btn');
        deleteButton.addEventListener('click', () => {
            deleteTask(task.id, taskCard);
        });
    });
}


function getBadgeClass(priority) {
    switch (priority) {
        case 'HIGH':
            return 'danger';
        case 'MEDIUM':
            return 'warning';
        case 'LOW':
            return 'primary';
        default:
            return 'secondary';
    }
}


function formatDueDate(dueDate) {
    if (!dueDate) return 'No due date';
    const date = new Date(dueDate);
    return date.toLocaleDateString() + ' ' + date.toLocaleTimeString();
}


function updateTaskStatus(taskId, status, listItem) {
    fetch(`http://localhost:8080/api/tasks/${taskId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ status: status }),
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to update task status');
            }
            return response.json();
        })
        .then(data => {
            console.log('Task status updated:', data);
            listItem.querySelector('span').textContent = `${data.title} - ${data.status}`;
        })
        .catch(error => {
            console.error('Error updating task status:', error);
        });
}




function showAlert(message, type = 'success') {
    const alertContainer = document.getElementById('alertContainer');
    const alert = document.createElement('div');
    alert.className = `alert alert-${type} alert-dismissible fade show`;
    alert.role = 'alert';
    alert.innerHTML = `
        ${message}
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    `;

    alertContainer.appendChild(alert);


    setTimeout(() => {
        alert.classList.remove('show');
        setTimeout(() => alert.remove(), 150);
    }, 5000);
}


function createTask(task) {
    fetch('http://localhost:8080/api/tasks', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(task),
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to create task');
            }
            return response.json();
        })
        .then(data => {
            console.log('Task created:', data);
            showAlert('Task added successfully!', 'success');
            fetchTasks('all');
        })
        .catch(error => {
            console.error('Error creating task:', error);
            showAlert('Error adding task. Please try again.', 'danger');
        });
}


function deleteTask(taskId, listItem) {
    fetch(`http://localhost:8080/api/tasks/${taskId}`, {
        method: 'DELETE',
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to delete task');
            }
            listItem.remove();
            console.log(`Task ${taskId} deleted`);
            showAlert('Task deleted successfully!', 'success');
        })
        .catch(error => {
            console.error('Error deleting task:', error);
            showAlert('Error deleting task. Please try again.', 'danger');
        });
}

