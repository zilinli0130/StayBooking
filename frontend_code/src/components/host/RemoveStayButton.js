/**
 * Copyright (c) 2023
 *
 * @summary Implementation of RemoveStayButton.js
 * @author Zilin Li
 * @date 2023-06-28
 *
 */

// Antd imports
import { message, Button } from "antd";

// React imports
import React from "react";

// Project imports
import { deleteStay } from "../../utils/backend_utils";


class RemoveStayButton extends React.Component {
    
    state = {
        loading: false,
    };
    
    handleRemoveStay = async () => {
        const { stay, onRemoveSuccess } = this.props;
        this.setState({
            loading: true,
        });
    
        try {
            await deleteStay(stay.id);
            onRemoveSuccess();
    
        } catch (error) {
            message.error(error.message);
        } finally {
            this.setState({
                loading: false,
            });
        }
    };
    
    render() {
        return (
            <Button
                loading={this.state.loading}
                onClick={this.handleRemoveStay}
                danger={true}
                shape="round"
                type="primary"
            >
                Remove Stay
            </Button>
        );
    }
}

export default RemoveStayButton;