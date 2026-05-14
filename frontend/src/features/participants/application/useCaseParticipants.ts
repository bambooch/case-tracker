import { useState } from 'react'

import {
  emptyParticipantDraft,
  type Participant,
  type ParticipantDraft,
  type ParticipantRole,
} from '../domain/participant'
import { addParticipant, removeParticipant, updateParticipant } from '../infrastructure/participantApi'

type ParticipantErrors = { add: string; edit: string; delete: string }
const emptyErrors: ParticipantErrors = { add: '', edit: '', delete: '' }

export function useCaseParticipants(caseId: number, initialParticipants: Participant[]) {
  const [participants, setParticipants] = useState<Participant[]>(initialParticipants)
  const [addDraft, setAddDraft] = useState<ParticipantDraft>(emptyParticipantDraft)
  const [editingPartyId, setEditingPartyId] = useState<number | null>(null)
  const [editRole, setEditRole] = useState<ParticipantRole | ''>('')
  const [errors, setErrors] = useState<ParticipantErrors>(emptyErrors)

  async function submitAdd() {
    if (!addDraft.partyId || !addDraft.role) return
    setErrors((e) => ({ ...e, add: '' }))
    try {
      const participant = await addParticipant(caseId, addDraft.partyId as number, addDraft.role as ParticipantRole)
      setParticipants((prev) => [...prev, participant])
      setAddDraft(emptyParticipantDraft)
    } catch {
      setErrors((e) => ({ ...e, add: 'Could not add participant.' }))
    }
  }

  function startEditing(participant: Participant) {
    setEditingPartyId(participant.partyId)
    setEditRole(participant.role as ParticipantRole)
    setErrors((e) => ({ ...e, edit: '' }))
  }

  function cancelEditing() {
    setEditingPartyId(null)
    setEditRole('')
  }

  async function submitEdit() {
    if (editingPartyId === null || !editRole) return
    setErrors((e) => ({ ...e, edit: '' }))
    try {
      const updated = await updateParticipant(caseId, editingPartyId, editRole as ParticipantRole)
      setParticipants((prev) => prev.map((p) => (p.partyId === updated.partyId ? updated : p)))
      cancelEditing()
    } catch {
      setErrors((e) => ({ ...e, edit: 'Could not update participant.' }))
    }
  }

  async function removeParticipantById(partyId: number) {
    setErrors((e) => ({ ...e, delete: '' }))
    try {
      await removeParticipant(caseId, partyId)
      setParticipants((prev) => prev.filter((p) => p.partyId !== partyId))
      if (editingPartyId === partyId) cancelEditing()
    } catch {
      setErrors((e) => ({ ...e, delete: 'Could not remove participant.' }))
    }
  }

  return {
    participants,
    addDraft,
    setAddDraft,
    editingPartyId,
    editRole,
    setEditRole,
    errors,
    submitAdd,
    startEditing,
    cancelEditing,
    submitEdit,
    removeParticipantById,
  }
}
