/**
 * Copyright (c) 2023
 *
 * @summary Implementation of ViewReservationButton.js
 * @author Zilin Li
 * @date 2023-06-28
 *
 */

import { Button, Modal } from "antd";
import React from "react";
import ReservationList from "./ReservationList";

class ViewReservationsButton extends React.Component {
    state = {
        modalVisible: false,
    };
    
    openModal = () => {
        this.setState({
        modalVisible: true,
        });
    };
    
    handleCancel = () => {
        this.setState({
        modalVisible: false,
        });
    };
    
    render() {
        const { stay } = this.props;
        const { modalVisible } = this.state;
        const modalTitle = `Reservations of ${stay.name}`;
    
        return (
            <>
                <Button onClick={this.openModal} shape="round">
                    View Reservations
                </Button>
                {modalVisible && (
                    <Modal
                        title={modalTitle}
                        centered={true}
                        visible={modalVisible}
                        closable={false}
                        footer={null}
                        onCancel={this.handleCancel}
                        destroyOnClose={true}
                    >
                        <ReservationList stayId={stay.id} />
                    </Modal>
                )}
            </>
        );
    }
}


export default ViewReservationsButton;