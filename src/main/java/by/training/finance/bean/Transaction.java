package by.training.finance.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Transaction implements Serializable {

	private static final long serialVersionUID = -5641364740381900177L;
	private long id;
	private long cardNumber;
	private String paymentPurpose;
	private BigDecimal amount;
	private Currency currency;
	private Date date;
	private boolean completed;
	private boolean writeOffOperation;

	public Transaction() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(long cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getPaymentPurpose() {
		return paymentPurpose;
	}

	public void setPaymentPurpose(String setPaymentPurpose) {
		this.paymentPurpose = setPaymentPurpose;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public boolean isWriteOffOperation() {
		return writeOffOperation;
	}

	public void setWriteOffOperation(boolean writeOffOperation) {
		this.writeOffOperation = writeOffOperation;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + (int) (cardNumber ^ (cardNumber >>> 32));
		result = prime * result + (completed ? 1231 : 1237);
		result = prime * result + ((currency == null) ? 0 : currency.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((paymentPurpose == null) ? 0 : paymentPurpose.hashCode());
		result = prime * result + (writeOffOperation ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transaction other = (Transaction) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (cardNumber != other.cardNumber)
			return false;
		if (completed != other.completed)
			return false;
		if (currency != other.currency)
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (id != other.id)
			return false;
		if (paymentPurpose == null) {
			if (other.paymentPurpose != null)
				return false;
		} else if (!paymentPurpose.equals(other.paymentPurpose))
			return false;
		if (writeOffOperation != other.writeOffOperation)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Transaction [id=" + id + ", cardNumber=" + cardNumber + ", paymentPurpose=" + paymentPurpose
				+ ", amount=" + amount + ", currency=" + currency + ", date=" + date + ", completed=" + completed
				+ ", writeOffOperation=" + writeOffOperation + "]";
	}

}
