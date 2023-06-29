/**
 * Copyright (c) 2023
 *
 * @summary Implementation of ReservationList.js
 * @author Zilin Li
 * @date 2023-06-28
 *
 */

// Antd importa
import { message, List } from "antd";
import Text from "antd/lib/typography/Text";

// React imports
import React from "react";

// Project imports
import { getReservationsByStay } from "../../utils/backend_utils";

class ReservationList extends React.Component {
    
    state = {
      loading: false,
      reservations: [],
    };
  
    componentDidMount() {
      this.loadData();
    }
  
    loadData = async () => {
      this.setState({
        loading: true,
      });
  
      try {
        const resp = await getReservationsByStay(this.props.stayId);
        this.setState({
          reservations: resp,
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

      const { loading, reservations } = this.state;
  
      return (
        <List
          loading={loading}
          dataSource={reservations}
          renderItem={(item) => (
            <List.Item>
              <List.Item.Meta
                title={<Text>Guest Name: {item.guest.username}</Text>}
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

  export default ReservationList;