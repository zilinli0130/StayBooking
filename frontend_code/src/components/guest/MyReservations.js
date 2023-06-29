/**
 * Copyright (c) 2023
 *
 * @summary Implementation of MyReservation.js
 * @author Zilin Li
 * @date 2023-06-28
 *
 */

// React imports
import React from "react";

// Antd imports
import { message, List, Typography } from "antd";

// Project imports
import { getReservations } from "../../utils/backend_utils";
import CancelReservationButton from "./CancelReservationButton ";

const { Text } = Typography;

class MyReservations extends React.Component {
    
    state = {
        loading: false,
        data: [],
    };

    componentDidMount() {
        this.loadData();
    }

    loadData = async () => {
        this.setState({
            loading: true,
        });

        try {
            const resp = await getReservations();
            this.setState({
                data: resp,
            });

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
        <List
          style={{ width: 1000, margin: "auto" }}
          loading={this.state.loading}
          dataSource={this.state.data}
          renderItem={(item) => (
            <List.Item 
              actions={[
                  <CancelReservationButton onCancelSuccess={this.loadData} reservationId={item.id} />,
              ]}
            >
              <List.Item.Meta
                title={<Text>{item.stay.name}</Text>}
                description={
                  <>
                    <Text>Checkin Date: {item.checkin_date}</Text>
                    <br />
                    <Text>Checkout Date: {item.checkout_date}</Text>
                  </>
                }
              />
            </List.Item>
          )}
        />
      );
    }
}

export default MyReservations;
