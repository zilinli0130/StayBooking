/**
 * Copyright (c) 2023
 *
 * @summary Implementation of CancelReservationButton.js
 * @author Zilin Li
 * @date 2023-06-28
 *
 */

// React imports
import React from "react";

// Antd imports
import {message, Button} from "antd";

// Project imports
import { cancelReservation } from "../../utils/backend_utils";

class CancelReservationButton extends React.Component {
    state = {
        loading: false,
    };

    handleCancelReservation = async () => {
        
        const { reservationId, onCancelSuccess } = this.props;
        this.setState({
            loading: true,
        });


        try {
            await cancelReservation(reservationId);

        } catch (error) {
            message.error(error.message);

        } finally {
            this.setState({
                loading: false,
            });
        }
        onCancelSuccess();
    };


    render() {
        return (
            <Button
                loading={this.state.loading}
                onClick={this.handleCancelReservation}
                danger={true}
                shape="round"
                type="primary"
            >
                Cancel Reservation
            </Button>
        );
    }
}

export default CancelReservationButton;
