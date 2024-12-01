import React from "react";

interface ApplicantsPopupProps {
    isVisible: boolean;
    onClose: () => void;
    participants: { name: string; email: string }[];
    remainingSlots: number;
}

const ApplicantsPopup: React.FC<ApplicantsPopupProps> = ({
                                                             isVisible,
                                                             onClose,
                                                             participants,
                                                             remainingSlots,
                                                         }) => {
    if (!isVisible) return null;

    return (
        <div className="popup-overlay" onClick={onClose}>
            <div className="popup-content" onClick={(e) => e.stopPropagation()}>
                <h3>Event Participants</h3>
                <button className="close-btn" onClick={onClose}>
                    Close
                </button>
                <ul>
                    {participants.map((participant, index) => (
                        <li key={index}>
                            {participant.name} ({participant.email})
                        </li>
                    ))}
                </ul>
                <p>
                    <strong>Remaining Slots:</strong> {remainingSlots}
                </p>
            </div>
        </div>
    );
};

export default ApplicantsPopup;
